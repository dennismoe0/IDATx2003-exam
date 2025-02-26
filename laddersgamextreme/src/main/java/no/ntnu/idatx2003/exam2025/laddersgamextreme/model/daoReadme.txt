"Data Access Objects (DAOs) or Repositories:
Create interfaces (and their implementations) for saving, updating, retrieving, and deleting your persistent data. For example, you might have a PlayerDAO interface with methods such as:
java
Copy
public interface PlayerDAO {
    void save(Player player);
    Player getById(int id);
    void update(Player player);
    void delete(Player player);
}
This pattern isolates your persistence logic from your domain logic. Later, when you integrate a database, you only need to change the DAO implementations without affecting your core classes."