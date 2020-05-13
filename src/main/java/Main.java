

import exception.DBException;
import model.User;
import service.UserService;

public class Main {
    public static void main(String[] args) throws Exception{
        fill();
    }

    private static void fill() throws DBException {
        UserService service = new UserService();
        service.cleanUp();
        service.createTable();
        service.addUser(new User("John", "john@mail", "pass"));
        service.addUser(new User("Jack", "jack@mail", "pass2"));
        service.addUser(new User("James", "james@mail", "pass3"));
        service.addUser(new User("Jo", "jo@mail", "pass4"));
    }
}
