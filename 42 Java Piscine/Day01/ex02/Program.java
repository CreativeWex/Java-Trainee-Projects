public class Program {
    public static void main(String[] args) {

        User john = new User("John", 500);
        User mike = new User("Mike", 500);
        User bob = new User("Bob", 100);

        UsersList usersList = new UsersList();
        usersList.add(john);
        usersList.add(mike);
        usersList.add(bob);
        usersList.display();

        System.out.println(usersList.findById(1));
        System.out.println(usersList.findByIndex(1));
        System.out.println(usersList.getAmount());

        UsersList bigUsersList = new UsersList();
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.display();
        System.out.println(bigUsersList.getAmount());

        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.add(john);
        bigUsersList.display();
        System.out.println(bigUsersList.getAmount());
    }
}
