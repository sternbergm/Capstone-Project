package htd.project.data;

import java.util.List;

public interface ObjectRepository<Obj> {

    List<Obj> readAll();

    Obj readById(int id);

    Obj create(Obj obj);

    boolean update(Obj obj);

    boolean delete(int id);
}
