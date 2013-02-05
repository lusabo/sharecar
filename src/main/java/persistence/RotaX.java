//package persistence;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.LineString;
//
//import entity.Coordenada;
//import entity.Rota;
//
//@Entity
//public class RotaX extends Rota {
//
//	@Column(name = "caminho")
//	private LineString lineString;
//
//	public LineString getLineString() {
//		if (this.lineString == null) {
//			List<Coordenada> coordenadas = this.getCaminho();
//			List<Coordinate> coordinates = new ArrayList<Coordinate>();
//
//			Coordinate coordinate;
//			for (Coordenada coordenada : coordenadas) {
//				coordinate = new Coordinate(coordenada.getLatitude(), coordenada.getLongitude());
//				coordinates.add(coordinate);
//			}
//
//			GeometryFactory gf = new GeometryFactory();
//			this.lineString = gf.createLineString(coordinates.toArray(new Coordinate[0]));
//		}
//
//		return this.lineString;
//	}
//
//	public void setLineString(LineString lineString) {
//		this.lineString = lineString;
//	}
//}
