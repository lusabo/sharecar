package service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import business.RotaBC;
import entity.Coordenada;
import entity.Route;
import entity.Usuario;

@Path("")
public class RouteService {

	@Inject
	private RotaBC routeBC;

	@POST
	@Path("/route")
	@Consumes("application/json;charset=UTF-8")
	public void create(@FormParam("id") Integer id, @FormParam("username") String username,
			@FormParam("description") String description, List<Coordenada> coords) {

		// System.out.println(caminho);
		//
		// Integer id = null;
		//
		// if (!idRota.equals("")) {
		// id = Integer.parseInt(idRota);
		// }
		//

		// GeometryFactory gf = new GeometryFactory();
		//
		// List<Coordenada> coordenadas = new ArrayList<Coordenada>();
		//
		// for (Coordenada coordenada : coordenadas) {
		// coordinate = new Coordinate(coordenada.getLatitude(), coordenada.getLongitude());
		// coordinates.add(coordinate);
		// }
		//
		// GeometryFactory gf = new GeometryFactory();
		// this.lineString = gf.createLineString(coordinates.toArray(new Coordinate[0]));
		// }

		// Coordinate c[] = new Coordinate[pontos.length];
		// String ap[];
		//
		//
		//
		// for (int i = 0; i < c.length; i++) {
		// ap = pontos[i].split(",");
		// c[i] = new Coordinate(Double.parseDouble(ap[0]), Double.parseDouble(ap[1]));
		// }

		/*
		 * [(1242342 234234),(2341234 1234234),(1234234 223423314)] [{lat = "dasfsdf"; lon = "asdasdasd" }, {}]
		 */

		// Rota r = new Rota(id, usuario, descricao, gf.createLineString(c));

		Usuario user = new Usuario(username);
		Route route = new Route(id, user, description, coords);

		routeBC.salvar(route); // @Produces( )

	}

	@GET
	@Path("/route")
	@Produces("application/json;charset=UTF-8")
	public List<Coordenada> create() {
		List<Coordenada> coordenadas = new ArrayList<Coordenada>();

		coordenadas.add(new Coordenada(1.1, 2.1));
		coordenadas.add(new Coordenada(4.0, 8.9));
		coordenadas.add(new Coordenada(0.1, 112.7));

		return coordenadas;
	}

	/*
	 * private static enum GeometryType { POINT("Point"), LINESTRING("LineString"), POLYGON("Polygon"),
	 * MULTIPOINT("MultiPoint"), MULTILINESTRING( "MultiLineString"), MULTIPOLYGON("MultiPolygon"),
	 * MULTIGEOMETRY("GeometryCollection"); private final String name; private GeometryType(String name) { this.name =
	 * name; } public String getName() { return name; } }
	 */

	/*
	 * private static GeometryType getGeometryType(Geometry geometry) { final Class<?> geomClass = geometry.getClass();
	 * final GeometryType returnValue; if (geomClass.equals(Point.class)) { returnValue = GeometryType.POINT; } else if
	 * (geomClass.equals(LineString.class)) { returnValue = GeometryType.LINESTRING; } else if
	 * (geomClass.equals(Polygon.class)) { returnValue = GeometryType.POLYGON; } else if
	 * (geomClass.equals(MultiPoint.class)) { returnValue = GeometryType.MULTIPOINT; } else if
	 * (geomClass.equals(MultiLineString.class)) { returnValue = GeometryType.MULTILINESTRING; } else if
	 * (geomClass.equals(MultiPolygon.class)) { returnValue = GeometryType.MULTIPOLYGON; } else if
	 * (geomClass.equals(GeometryCollection.class)) { returnValue = GeometryType.MULTIGEOMETRY; } else { returnValue =
	 * null; // HACK!!! throw exception. } return returnValue; }
	 */

}
