import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Pet> pets = getPetsFromDatabase();

        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }

    private static List<Pet> getPetsFromDatabase() {
        List<Pet> pets = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/fp2_23b";
        String user = "fp2_23b";
        String password = ""; 

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM pets";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String birthDate = resultSet.getString("birth_date");
                    int typeId = resultSet.getInt("type_id");
                    int ownerId = resultSet.getInt("owner_id");

                    Pet pet = new Pet(id, name, birthDate, typeId, ownerId);
                    pets.add(pet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }
}

class Pet {
    private int id;
    private String name;
    private String birthDate;
    private int typeId;
    private int ownerId;

    public Pet(int id, String name, String birthDate, int typeId, int ownerId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.typeId = typeId;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", typeId=" + typeId +
                ", ownerId=" + ownerId +
                '}';
    }
}
