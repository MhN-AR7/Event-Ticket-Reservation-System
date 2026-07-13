package ir.maktabsharif.event.model;

public abstract class BaseModel {
    private Long id;

    public BaseModel(Long id) {
        this.id = id;
    }

    public BaseModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
