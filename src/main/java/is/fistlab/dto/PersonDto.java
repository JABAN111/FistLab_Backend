package is.fistlab.dto;

import is.fistlab.database.entities.Location;
import lombok.Data;

@Data
public class PersonDto {
    private Long id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String eyeColor; //Поле может быть null
    private String hairColor; //Поле может быть null
    private Location location; //Поле не может быть null
    private int height; //Значение поля должно быть больше 0
    private long weight; //Значение поля должно быть больше 0
    private String nationality;
    //предполагается, что пользователь вводит свое значение, выданное ему в жизни(пример ису ид)
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 10, Поле не может быть null
}