package nl.novi.vinylshop.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BaseModel {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public BaseModel() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    // Afgeleide data: Aantal dagen sinds creatie
    public long getDaysSinceCreated() {
        return ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    // Afgeleide data: Is dit object bewerkt?
    public boolean isEdited() {
        return editDate != null && !editDate.equals(createDate);
    }

}
