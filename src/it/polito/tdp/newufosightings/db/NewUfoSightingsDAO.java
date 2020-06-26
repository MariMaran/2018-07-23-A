package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Arco;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {
	
	public List<Arco> getArchi(int anno, String forma, Map<String, State> idMap){
		String sql="SELECT s1.state AS stato1, s2.state AS stato2, COUNT(*) AS peso "+
				"FROM sighting s1, sighting s2, neighbor n "+
				"WHERE YEAR(s1.datetime)=? && YEAR(s2.datetime)=? && s1.shape=? && s2.shape=? && n.state1=s1.state && n.state2=s2.state "+
				"GROUP BY s1.state, s2.state";
		List<Arco> list=new ArrayList();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setString(3, forma);
			st.setString(4, forma);
			ResultSet res = st.executeQuery();
			

			while (res.next()) {
	
				State s1=idMap.get(res.getString("stato1").toUpperCase());
				State s2=idMap.get(res.getString("stato2").toUpperCase());

				int peso=res.getInt("peso");
					list.add(new Arco(s1,s2,peso));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return list;
		
	}
	
	
	public List<String> getFormeAnno(int anno) {
		String sql="SELECT DISTINCT s.shape as forma "+
				"FROM sighting s "+
				"WHERE YEAR(s.datetime)=? && s.shape!='' ";
		List<String> list=new ArrayList();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(res.getString("forma"));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return list;
		
		
	}

	public List<Sighting> loadSightings(int anno, String forma) {
		String sql = "SELECT * FROM sighting WHERE YEAR(datetime)=? && shape=?";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			st.setInt(1,anno);
			st.setString(2, forma);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates() {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
