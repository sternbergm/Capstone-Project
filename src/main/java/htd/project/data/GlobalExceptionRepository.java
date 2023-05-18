package htd.project.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import htd.project.models.Error;

public interface GlobalExceptionRepository {

    boolean add(Error error);

    List<Error> getAll();
}
