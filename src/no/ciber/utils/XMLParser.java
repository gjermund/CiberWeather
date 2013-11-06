package no.ciber.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import no.ciber.data.WeatherData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

	public static void parseForecast(String xmlString, WeatherData weatherData) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlString);
			
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
		Nodes textNodes = new Nodes(locationNode.getChildNodes());
		for (Node textNode : textNodes) {
			
		}
	}

	private static void parseTabularForecast(WeatherData weatherData, Node tabularForecast) {
		
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