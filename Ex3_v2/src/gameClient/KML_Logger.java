package gameClient;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.json.JSONException;
import Server.game_service;
import dataStructure.graph;
import dataStructure.edge_data;
import dataStructure.node_data;

public class KML_Logger {
	graph graph;
	game_service game;
	MyGame kGame;
	String KML = "";

	public KML_Logger() {

	}
	
	public KML_Logger(MyGame g) {
		this.kGame=g;
		this.game=kGame.getGame();
		this.graph=kGame.getGraph();
	}

	public void setGame(game_service game) {
		this.game = game;
	}
	public game_service getGame() {
		return this.game;
	}
	public void setGraph(graph graph) {
		this.graph=graph;
	}
	public graph getGraph() {
		return this.graph;
	}

	public void createENKML() {
		Collection<node_data> nodes =graph.getV();

		String up="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\r\n" + 
				"<Document>\r\n" + 
				"	<name>icon.kml</name>\r\n" + 
				"	<Style id=\"sh_wheel_chair_accessible\">\r\n" + 
				"		<IconStyle>\r\n" + 
				"			<color>ffffaa00</color>\r\n" + 
				"			<scale>1.4</scale>\r\n" + 
				"			<Icon>\r\n" + 
				"				<href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" + 
				"			</Icon>\r\n" + 
				"			<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
				"		</IconStyle>\r\n" + 
				"		<BalloonStyle>\r\n" + 
				"		</BalloonStyle>\r\n" + 
				"		<ListStyle>\r\n" + 
				"		</ListStyle>\r\n" + 
				"	</Style>\r\n" + 
				"	<StyleMap id=\"msn_wheel_chair_accessible\">\r\n" + 
				"		<Pair>\r\n" + 
				"			<key>normal</key>\r\n" + 
				"			<styleUrl>#sn_wheel_chair_accessible</styleUrl>\r\n" + 
				"		</Pair>\r\n" + 
				"		<Pair>\r\n" + 
				"			<key>highlight</key>\r\n" + 
				"			<styleUrl>#sh_wheel_chair_accessible</styleUrl>\r\n" + 
				"		</Pair>\r\n" + 
				"	</StyleMap>\r\n" + 
				"	<Style id=\"sn_wheel_chair_accessible\">\r\n" + 
				"		<IconStyle>\r\n" + 
				"			<color>ffffaa00</color>\r\n" + 
				"			<scale>1.2</scale>\r\n" + 
				"			<Icon>\r\n" + 
				"				<href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" + 
				"			</Icon>\r\n" + 
				"			<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
				"		</IconStyle>\r\n" + 
				//				"		<BalloonStyle>\r\n" + 
				//				"		</BalloonStyle>\r\n" + 
				"		<ListStyle>\r\n" + 
				"		</ListStyle>\r\n" + 
				"	</Style>\r\n" + 
				"		<name>ED1.kml</name>\r\n" + 
				"	<StyleMap id=\"m_ylw-pushpin\">\r\n" + 
				"		<Pair>\r\n" + 
				"			<key>normal</key>\r\n" + 
				"			<styleUrl>#s_ylw-pushpin</styleUrl>\r\n" + 
				"		</Pair>\r\n" + 
				"		<Pair>\r\n" + 
				"			<key>highlight</key>\r\n" + 
				"			<styleUrl>#s_ylw-pushpin_hl</styleUrl>\r\n" + 
				"		</Pair>\r\n" + 
				"	</StyleMap>\r\n" + 
				"	<Style id=\"s_ylw-pushpin_hl\">\r\n" + 
				"		<IconStyle>\r\n" + 
				"			<scale>1.3</scale>\r\n" + 
				"			<Icon>\r\n" + 
				"				<href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" + 
				"			</Icon>\r\n" + 
				"			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"		</IconStyle>\r\n" + 
				"		<LineStyle>\r\n" + 
				"			<color>ff0055ff</color>\r\n" + 
				"			<width>2.8</width>\r\n" + 
				"		</LineStyle>\r\n" + 
				"	</Style>\r\n" + 
				"	<Style id=\"s_ylw-pushpin\">\r\n" + 
				"		<IconStyle>\r\n" + 
				"			<scale>1.1</scale>\r\n" + 
				"			<Icon>\r\n" + 
				"				<href>http://maps.google.com/mapfiles/kml/paddle/purple-stars.png</href>\r\n" + 
				"			</Icon>\r\n" + 
				"			<hotSpot x=\"20\" y=\"2\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"		</IconStyle>\r\n" + 
				"		<LineStyle>\r\n" + 
				"			<color>ff0055ff</color>\r\n" + 
				"			<width>2.8</width>\r\n" + 
				"		</LineStyle>\r\n" + 
				"	</Style>\r\n";


		String vertex = "";
		String edges = "";
		for (node_data n : nodes) {
			vertex += "<Placemark>\r\n" + 
					"		<name>"+n.getKey()+"</name>\r\n" + 
					//					"		<LookAt>\r\n" + 
					//					"			<longitude>-72.30046907314483</longitude>\r\n" + 
					//					"			<latitude>21.49729253227983</latitude>\r\n" + 
					//					"			<altitude>0</altitude>\r\n" + 
					//					"			<heading>7.870538936204015</heading>\r\n" + 
					//					"			<tilt>34.26108609844667</tilt>\r\n" + 
					//					"			<range>23570.30329618517</range>\r\n" + 
					//					"			<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\r\n" + 
					//					"		</LookAt>\r\n" + 
					"		<styleUrl>#m_ylw-pushpin</styleUrl>\r\n" + 
					"		<Point>\r\n" + 
					"			<gx:drawOrder>1</gx:drawOrder>\r\n" + 
					"			<coordinates>"+n.getLocation().x()+","+n.getLocation().y()+",0</coordinates>\r\n" + 
					"		</Point>\r\n" + 
					"	</Placemark>\r\n" + 
					"	";
		}
		for (node_data n : nodes) {
			Collection<edge_data> gEdges =graph.getE(n.getKey());
			for (edge_data edge : gEdges) {
				double x1=graph.getNode(edge.getSrc()).getLocation().x();
				double y1=graph.getNode(edge.getSrc()).getLocation().y();
				double x2=graph.getNode(edge.getDest()).getLocation().x();
				double y2=graph.getNode(edge.getDest()).getLocation().y();
				edges += "<Placemark>\r\n" + 
						"		<name>"+edge.getWeight()+"</name>\r\n" + 
						"		<styleUrl>#m_ylw-pushpin</styleUrl>\r\n" + 
						"		<LineString>\r\n" + 
						"			<tessellate>1</tessellate>\r\n" + 
						"			<coordinates>\r\n" + 
						"				"+x1+","+y1+",0 "+x2+","+y2+",0 \r\n" + 
						"			</coordinates>\r\n" + 
						"		</LineString>\r\n" + 
						"	</Placemark>";
			}
		}

		KML += up+edges+vertex;
	}	

	public void createRFKML() {
		String now = java.time.LocalDate.now()+"T"+java.time.LocalTime.now();
		String after ="";
		LocalTime t1 = LocalTime.now();
		t1=t1.plusNanos(100*1000000);
		after = java.time.LocalDate.now()+"T"+t1;
		Collection<Bots> robots = kGame.getRobotes();
		ArrayList<Fruit> fruits = kGame.getFruits();
		String robot = "";
		for (Bots kBot: robots) {
			robot += "	<name>Robot.kml</name> \r\n" + 
					"					<Style id=\"sn_motorcycling\"> \r\n" + 
					"						<IconStyle>\r\n" + 
					"							<color>ff00aa00</color> \r\n" + 
					"							<Icon>\r\n" + 
					"								<href>http://maps.google.com/mapfiles/kml/shapes/hiker.png</href>\r\n" + 
					"							</Icon>\r\n" + 
					"							<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
					"						</IconStyle>\r\n" + 
					"						<BalloonStyle>\r\n" + 
					"						</BalloonStyle>\r\n" + 
					"						<ListStyle>\r\n" + 
					"						</ListStyle>\r\n" + 
					"					</Style>\r\n" + 
					"					<Style id=\"sh_motorcycling\">\r\n" + 
					"						<IconStyle>\r\n" + 
					"							<color>ff00aa00</color>\r\n" + 
					"							<scale>0.9</scale>\r\n" + 
					"							<Icon>\r\n" + 
					"								<href>http://maps.google.com/mapfiles/kml/shapes/hiker.png</href>\r\n" + 
					"							</Icon>\r\n" + 
					"							<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
					"						</IconStyle>\r\n" + 
					"						<BalloonStyle>\r\n" + 
					"						</BalloonStyle>\r\n" + 
					"						<ListStyle>\r\n" + 
					"						</ListStyle>\r\n" + 
					"					</Style>\r\n" + 
					"					<StyleMap id=\"msn_motorcycling\">\r\n" + 
					"						<Pair>\r\n" + 
					"							<key>normal</key>\r\n" + 
					"							<styleUrl>#sn_motorcycling</styleUrl>\r\n" + 
					"						</Pair>\r\n" + 
					"						<Pair>\r\n" + 
					"							<key>highlight</key>\r\n" + 
					"							<styleUrl>#sh_motorcycling</styleUrl>\r\n" + 
					"						</Pair>\r\n" + 
					"					</StyleMap>\r\n\""+ 
					"		<Placemark>\r\n" + 
					"		<name>"+kBot.id+"</name>\r\n" + 
					"		<LookAt>\r\n" + 
					"			<longitude>35.20057915651626</longitude>\r\n" + 
					"			<latitude>32.10642421692141</latitude>\r\n" + 
					"			<altitude>0</altitude>\r\n" + 
					"			<heading>0.001400841528527689</heading>\r\n" + 
					"			<tilt>48.82445584719025</tilt>\r\n" + 
					"			<range>2429.727375786245</range>\r\n" + 
					"			<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\r\n" + 
					"		</LookAt>\r\n" +
					"		<TimeSpan>\r\n" + 
					"            <begin>"+now+"</begin>\r\n" + 
					"            <end>"+after+"</end>\r\n" + 
					"        </TimeSpan>" +
					"		<styleUrl>#msn_motorcycling</styleUrl>\r\n" + 
					"		<Point>\r\n" + 
					"			<gx:drawOrder>1</gx:drawOrder>\r\n" + 
					"			<coordinates>"+kBot.getLocation().x()+","+kBot.getLocation().y()+",0</coordinates>\r\n" + 
					"		</Point>\r\n" + 
					"	</Placemark>";
		}

		String fruit = "" ;
		for (Fruit kFruit : fruits) {
			fruit += "	<name>fruit.kml</name>\r\n" + 
					"					<StyleMap id=\"msn_dollar\">\r\n" + 
					"						<Pair>\r\n" + 
					"							<key>normal</key>\r\n" + 
					"							<styleUrl>#sn_dollar</styleUrl>\r\n" + 
					"						</Pair>\r\n" + 
					"						<Pair>\r\n" + 
					"							<key>highlight</key>\r\n" + 
					"							<styleUrl>#sh_dollar</styleUrl>\r\n" + 
					"						</Pair>\r\n" + 
					"					</StyleMap>\r\n" + 
					"					<Style id=\"sn_dollar\">\r\n" + 
					"						<IconStyle>\r\n" + 
					"							<color>ff7f00ff</color>\r\n" + 
					"							<scale>0.945455</scale>\r\n" + 
					"							<Icon>\r\n" + 
					"								<href>http://maps.google.com/mapfiles/kml/shapes/parks.png</href>\r\n" + 
					"							</Icon>\\r\\n\" + \r\n" + 
					"							<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
					"						</IconStyle>\r\n" + 
					"						<BalloonStyle>\r\n" + 
					"						</BalloonStyle>\r\n" + 
					"						<ListStyle>\r\n" + 
					"						</ListStyle>\r\n" + 
					"					</Style>\r\n" + 
					"					<Style id=\"sh_dollar\">\r\n" + 
					"						<IconStyle>\r\n" + 
					"							<color>ff7f00ff</color>\r\n" + 
					"							<scale>0.8</scale>\r\n" + 
					"							<Icon>\r\n" + 
					"								<href>http://maps.google.com/mapfiles/kml/shapes/parks.png</href>\r\n" + 
					"							</Icon>\r\n" + 
					"							<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\r\n" + 
					"						</IconStyle>\r\n" + 
					"						<BalloonStyle>\r\n" + 
					"						</BalloonStyle>\r\n" + 
					"						<ListStyle>\r\n" + 
					"						</ListStyle>\r\n" + 
					"					</Style>"+ 
					"	<Placemark>\r\n" + 
					"		<name>"+kFruit.getType()+"</name>\r\n" + 
					"		<LookAt>\r\n" + 
					"			<longitude>35.20049765372926</longitude>\r\n" + 
					"			<latitude>32.10519390930328</latitude>\r\n" + 
					"			<altitude>0</altitude>\r\n" + 
					"			<heading>0.04871208709312937</heading>\r\n" + 
					"			<tilt>48.08517247511922</tilt>\r\n" + 
					"			<range>2516.536413792175</range>\r\n" + 
					"			<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\r\n" + 
					"		</LookAt>\r\n" +
					"		<TimeSpan>\r\n" + 
					"            <begin>"+now+"</begin>\r\n" + 
					"            <end>"+after+"</end>\r\n" + 
					"        </TimeSpan>" +
					"		<styleUrl>#msn_dollar</styleUrl>\r\n" + 
					"		<Point>\r\n" + 
					"			<gx:drawOrder>1</gx:drawOrder>\r\n" + 
					"			<coordinates>"+kFruit.getLocation().x()+","+kFruit.getLocation().y()+",0</coordinates>\r\n" + 
					"		</Point>\r\n" + 
					"	</Placemark>";
		}
		KML += robot+fruit;
		save(KML);
	}

	public void save(String k) {
		try {
			k+="</Document>\r\n" + 
					"</kml>";
			FileWriter fw = new FileWriter("test.kml");
			fw.write(k);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

