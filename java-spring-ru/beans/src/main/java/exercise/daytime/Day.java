package exercise.daytime;
import jakarta.annotation.PostConstruct;

public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    @PostConstruct
    public void init() {
        System.out.println("Bean created!");
    }
}
