package common.data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Space marine class.
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    /**
     * Identification number of a space marine.
     */
    private Long id;
    /**
     * Name of a space marine.
     */
    private String name;
    /**
     * Coordinates of a space marine.
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Creation date of a space marine.
     * Generates automatically.
     */
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Health value of a space marine.
     * Must be more than 0.
     */
    private Integer health; //Поле не может быть null, Значение поля должно быть больше 0
    /**
     * Heart count value of a space marine.
     * Must be more than 0, max value is 3.
     */
    private long heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    /**
     * Height value of a space marine
     */
    private Long height; //Поле может быть null
    /**
     * Melee weapon of a space marine.
     * Must be equal to 3 values of the enum class.
     */
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    /**
     * Chapter of a space marine.
     */
    private Chapter chapter; //Поле не может быть null

    /**
     * Id setter.
     * @param id Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Name setter.
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Coordinates setter.
     * @param coordinates Coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Creation date setter.
     * @param creationDate Creation date
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Health setter.
     * @param health Health
     */
    public void setHealth(Integer health) {
        this.health = health;
    }

    /**
     * Heart count setter.
     * @param heartCount Heart count
     */
    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    /**
     * Height setter.
     * @param height Height
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * Melee weapon setter.
     * @param meleeWeapon Melee weapon
     */
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    /**
     * Chapter setter.
     * @param chapter Chapter
     */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * Space marine class constructor.
     * @param id Id
     * @param name Name
     * @param coordinates Coordinates
     * @param creationDate Creation date
     * @param health Health
     * @param heartCount Heart count
     * @param height Height
     * @param meleeWeapon Melee Weapon
     * @param chapter Chapter
     */
    public SpaceMarine(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Integer health, long heartCount, Long height, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.health = health;
        this.heartCount = heartCount;
        this.height = height;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    /**
     * Empty constructor for space marine.
     */
    public SpaceMarine() {

    }

    /**
     * Id getter.
     * @return Id of a space marine.
     */
    public Long getId() {
        return id;
    }

    /**
     * Name getter.
     * @return name of a space marine.
     */
    public String getName() {
        return name;
    }

    /**
     * Coordinates getter.
     * @return Coordinates class.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Creation date getter.
     * @return Creation date.
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Health getter.
     * @return Health.
     */
    public Integer getHealth() {
        return health;
    }

    /**
     * Hearth count getter.
     * @return Hearth count.
     */
    public long getHeartCount() {
        return heartCount;
    }

    /**
     * Height getter.
     * @return Height.
     */
    public Long getHeight() {
        return height;
    }

    /**
     * Melee weapon getter.
     * @return Melee weapon.
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * Chapter getter.
     * @return Chapter.
     */
    public Chapter getChapter() {
        return chapter;
    }



    @Override
    public String toString() {
        return String.format("Id: %d\nName: %s\nCoordinates: {x: %f y: %f}\nCreation Time: %s\nHealth: %d\nHeart count: %d\nHeight: %d\nMelee weapon: %s\nChapter: name: %s\n         world: %s",
                getId(),
                getName(),
                getCoordinates().getX(),
                getCoordinates().getY(),
                getCreationDate(),
                getHealth(),
                getHeartCount(),
                getHeight(),
                getMeleeWeapon(),
                getChapter().getName(),
                getChapter().getWorld());

    }

    @Override
    public int compareTo(SpaceMarine o) {
        return (int) (this.height - o.height);
    }
}
