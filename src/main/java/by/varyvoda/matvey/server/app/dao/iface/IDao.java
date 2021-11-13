package by.varyvoda.matvey.server.app.dao.iface;

import by.varyvoda.matvey.server.app.dao.query.Query;

import java.util.List;

public interface IDao<V> {

	List<V> get(Query<V> criteria);

	List<V> getAll();

	void save(V appliance);

	void update(V appliance);

}
