package by.dm13y.study.orm.entity;

public abstract class DataSet<T> {
    protected long id;

    public long getId(){
        return id;
    }

    public abstract String getTableName();
    public abstract String getIdColumnName();

    public void setId(long id){
        this.id = id;
    }

}
