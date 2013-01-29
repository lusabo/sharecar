package service;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import business.RotaBC;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;

import entity.Rota;
import entity.Usuario;


@Path("")
public class RotaService {

	private static final long serialVersionUID = 1L;

	@Inject
	private RotaBC rotaBC;
	
	@GET
	@Path("/rotas/{idrota}")
	@Produces("application/json")
	public Rota show(@PathParam("idrota") String idRota) {
		Integer id = null;
		if (!idRota.equals("")) {
			id = Integer.parseInt(idRota);
		}
		Rota r = rotaBC.show(id);
		
		GeometryType geometryType = getGeometryType(r.getCaminho());
		
		System.out.println("###################" + geometryType.name);
		
		return r;	
	}

	@POST
	@Path("/rotas")
	public void create(@FormParam("idrota") String idRota, @FormParam("cpfusuario") String cpfUsuario,
			@FormParam("descricao") String descricao, @FormParam("geometria[]") String[] pontos) throws ParseException {

		Integer id = null;

		if (!idRota.equals("")) {
			id = Integer.parseInt(idRota);
		}

		Usuario usuario = new Usuario(cpfUsuario);

		GeometryFactory gf = new GeometryFactory();

		Coordinate c[] = new Coordinate[pontos.length];
		String ap[];
		for (int i = 0; i < c.length; i++) {
			ap = pontos[i].split(",");
			c[i] = new Coordinate(Double.parseDouble(ap[0]), Double.parseDouble(ap[1]));
		}

		Rota r = new Rota(id, usuario, descricao, gf.createLineString(c));
		
		rotaBC.create(r);

	}
	
	 private static enum GeometryType {
	        POINT("Point"),
	        LINESTRING("LineString"),
	        POLYGON("Polygon"),
	        MULTIPOINT("MultiPoint"),
	        MULTILINESTRING("MultiLineString"),
	        MULTIPOLYGON("MultiPolygon"),
	        MULTIGEOMETRY("GeometryCollection");

	        private final String name;

	        private GeometryType(String name) {
	            this.name = name;
	        }

	        public String getName() {
	            return name;
	        }
	    }
	
	private static GeometryType getGeometryType(Geometry geometry) {
        final Class<?> geomClass = geometry.getClass();
        final GeometryType returnValue;

        if (geomClass.equals(Point.class)) {
            returnValue = GeometryType.POINT;
        } else if (geomClass.equals(LineString.class)) {
            returnValue = GeometryType.LINESTRING;
        } else if (geomClass.equals(Polygon.class)) {
            returnValue = GeometryType.POLYGON;
        } else if (geomClass.equals(MultiPoint.class)) {
            returnValue = GeometryType.MULTIPOINT;
        } else if (geomClass.equals(MultiLineString.class)) {
            returnValue = GeometryType.MULTILINESTRING;
        } else if (geomClass.equals(MultiPolygon.class)) {
            returnValue = GeometryType.MULTIPOLYGON;
        } else if (geomClass.equals(GeometryCollection.class)) {
            returnValue = GeometryType.MULTIGEOMETRY;
        } else {
            returnValue = null;
            //HACK!!! throw exception.
        }

        return returnValue;
    }   

}
