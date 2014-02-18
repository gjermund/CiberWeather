package no.ciber.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import no.ciber.data.TabularForecast;
import no.ciber.data.TextForecast;
import no.ciber.data.WeatherData;
import no.ciber.data.tabular.Precipitation;
import no.ciber.data.tabular.Pressure;
import no.ciber.data.tabular.Symbol;
import no.ciber.data.tabular.Temperature;
import no.ciber.data.tabular.WindDirection;
import no.ciber.data.tabular.WindSpeed;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    private static SimpleDateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void parseForecast(String xmlString, WeatherData weatherData) {
		try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			
			Element weatherDataElement = document.getDocumentElement();
			Node forecastNode = getChildNodeByName(weatherDataElement, "forecast");

			Nodes forecasts = new Nodes(forecastNode.getChildNodes());
			
			Node textForecast = forecasts.getByName("text");
			Node tabularForecast = forecasts.getByName("tabular");
			
			parseTextForecast(weatherData, textForecast);
			parseTabularForecast(weatherData, tabularForecast);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void parseTextForecast(WeatherData weatherData, Node textForecast) {
		Node locationNode = getChildNodeByName(textForecast, "location");
		Nodes timeNodes = new Nodes(locationNode.getChildNodes());
		for (Node timeNode : timeNodes) {
			Date from = parseDate(timeNode, "from");
			Date to = parseDate(timeNode, "to");
			Nodes textNodes = new Nodes(timeNode.getChildNodes());
			String title = textNodes.getByName("title").getTextContent();
			String body = textNodes.getByName("body").getTextContent();
			weatherData.addTextForecast(new TextForecast(from, to, title, body));
		}
	}

	private static void parseTabularForecast(WeatherData weatherData, Node tabularForecast) {
		Nodes timeNodes = new Nodes(tabularForecast.getChildNodes());
		for (Node timeNode : timeNodes) {
			Date from = parseDate(timeNode, "from");
			Date to = parseDate(timeNode, "to");
			Nodes tabularNodes = new Nodes(timeNode.getChildNodes());
			Symbol symbol = parseSymbol(tabularNodes);
			Precipitation precipitation = parsePrecipitation(tabularNodes);
			WindDirection windDirection = parseWindDirection(tabularNodes);
			WindSpeed windSpeed = parseWindSpeed(tabularNodes);
			Temperature temperature = parseTemperature(tabularNodes);
			Pressure pressure = parsePressure(tabularNodes);
			weatherData.addTabularForecast(new TabularForecast(from, to, symbol, precipitation, windDirection, windSpeed, temperature, pressure));
		}
	}
	
	private static Symbol parseSymbol(Nodes tabularNodes) {
		Node symbolNode = tabularNodes.getByName("symbol");
		Integer number = parseInt(symbolNode, "number");
		String name = parseString(symbolNode, "name");
		return new Symbol(number, name);
	}

	private static Precipitation parsePrecipitation(Nodes tabularNodes) {
		Node precipitationNode = tabularNodes.getByName("precipitation");
		Double value = parseDouble(precipitationNode, "value");
		Double minValue = parseDouble(precipitationNode, "minvalue");
		Double maxValue = parseDouble(precipitationNode, "maxvalue");
		return new Precipitation(value, minValue, maxValue);
	}

	private static WindDirection parseWindDirection(Nodes tabularNodes) {
		Node windDirectionNode = tabularNodes.getByName("windDirection");
		Double degrees = parseDouble(windDirectionNode, "deg");
		String code = parseString(windDirectionNode, "code");
		String name = parseString(windDirectionNode, "name");
		return new WindDirection(degrees, code, name);
	}

	private static WindSpeed parseWindSpeed(Nodes tabularNodes) {
		Node windSpeedNode = tabularNodes.getByName("windSpeed");
		Double metersPerSecond = parseDouble(windSpeedNode, "mps");
		String name = parseString(windSpeedNode, "name");
		return new WindSpeed(metersPerSecond, name);
	}

	private static Temperature parseTemperature(Nodes tabularNodes) {
		Node temperatureNode = tabularNodes.getByName("temperature");
		String unit = parseString(temperatureNode, "unit");
		Double value = parseDouble(temperatureNode, "value");
		return new Temperature(unit, value);
	}

	private static Pressure parsePressure(Nodes tabularNodes) {
		Node pressureNode = tabularNodes.getByName("pressure");
		String unit = parseString(pressureNode, "unit");
		Double value = parseDouble(pressureNode, "value");
		return new Pressure(unit, value);
	}

	private static String parseString(Node node, String attributeName) {
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute == null) return null;
		return attribute.getTextContent();
	}
	
	private static Integer parseInt(Node node, String attributeName) {
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute == null) return null;
		return Integer.parseInt(attribute.getTextContent());
	}
	
	private static Double parseDouble(Node node, String attributeName) {
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute == null) return null;
		return Double.parseDouble(attribute.getTextContent());
	}
	
	private static Date parseDate(Node node, String attributeName) {
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute == null) return null;
		try {
            
			return dateFormat.parse(attribute.getTextContent());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Node getChildNodeByName(Node parent, String name) {
		return new Nodes(parent.getChildNodes()).getByName(name);
	}
	
	// Wrapper for nodelist, ignoring all textnodes (effectively to avoid whitespace)
	private static class Nodes implements Iterable<Node> {

		private List<Node> nodes = new ArrayList<Node>();
		private Map<String, Node> nodeMap = new HashMap<String, Node>(); 

		public Nodes(NodeList nodeList) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() != Node.TEXT_NODE) {
					nodes.add(node);
					nodeMap.put(node.getNodeName(), node);
				}
			}
		}
		
		public Node getByName(String name) {
			if (!nodeMap.containsKey(name)) throw new IllegalStateException();
			return nodeMap.get(name);
		}

		@Override
		public Iterator<Node> iterator() {
			return nodes.iterator();
		}
	}
}