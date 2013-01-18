package service;

import java.text.ParseException;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import business.RotaBC;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

import entity.Rota;
import entity.Usuario;

@Path("")
public class RotaService {

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

}
