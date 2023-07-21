public class User {
    private Integer id;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.id = UserIdsGenerator.getInstance().getUuid();
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Id: " + id
                + "\nName: " + name
                + "\nBalance: " + balance + "\n";
    }
    public void display() {

    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBalance(Integer balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new RuntimeException("Balance cant be negative");
        }

    }
}
