package htd.project.data;

import java.time.LocalDateTime;

public interface GlobalExceptionRepository {

    boolean add(LocalDateTime timestamp, String stackTrace);
}
