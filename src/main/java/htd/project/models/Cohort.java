package htd.project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cohort {

    private int cohortId;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "client cannot be null")
    private Client client;

    @NotNull(message = "Instructor cannot be null")
    private Instructor instructor;

    @NotNull(message = "contractors cannot be null")
    private List<Contractor> contractors;

    @NotNull(message = "Modules cannot be null")
    private List<Module> modules;

}
