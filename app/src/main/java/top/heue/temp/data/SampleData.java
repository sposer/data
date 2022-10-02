package top.heue.temp.data;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class SampleData {
    @Id
    public long id;
    public String text;
}
