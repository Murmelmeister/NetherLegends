package de.murmelmeister.citybuild.api;

import de.murmelmeister.murmelapi.utils.Database;

public class Economy {

    public Economy() {
        String tableName = "CB_Economy";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "UserID INT PRIMARY KEY, Money DOUBLE, BankMoney DOUBLE");
    }

    public boolean existUser(int userId) {
        return Database.callExists(Procedure.ECONOMY_GET_USER.getName(), userId);
    }

    public void createUser(int userId) {
        if (existUser(userId)) return;
        Database.callUpdate(Procedure.ECONOMY_CREATE_USER.getName(), userId, 0, 0);
    }

    public void deleteUser(int userId) {
        Database.callUpdate(Procedure.ECONOMY_DELETE_USER.getName(), userId);
    }

    public double getMoney(int userId) {
        return Database.callQuery(0.0D, "Money", double.class, Procedure.ECONOMY_GET_USER.getName(), userId);
    }

    public double getBankMoney(int userId) {
        return Database.callQuery(0.0D, "BankMoney", double.class, Procedure.ECONOMY_GET_USER.getName(), userId);
    }

    public void setMoney(int userId, double amount) {
        Database.callUpdate(Procedure.ECONOMY_UPDATE_MONEY.getName(), userId, amount);
    }

    public void setBankMoney(int userId, double amount) {
        Database.callUpdate(Procedure.ECONOMY_UPDATE_BANK.getName(), userId, amount);
    }

    public void addMoney(int userId, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        double current = getMoney(userId);
        current += amount;
        setMoney(userId, current);
    }

    public void addBankMoney(int userId, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        double current = getBankMoney(userId);
        current += amount;
        setBankMoney(userId, current);
    }

    public void removeMoney(int userId, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        double current = getMoney(userId);
        current -= amount;
        setMoney(userId, current);
    }

    public void removeBankMoney(int userId, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        double current = getBankMoney(userId);
        current -= amount;
        setBankMoney(userId, current);
    }

    public void resetMoney(int userId) {
        setMoney(userId, 0);
    }

    public void resetBankMoney(int userId) {
        setBankMoney(userId, 0);
    }

    public boolean hasEnoughMoney(int userId, double money) {
        if (money < 0) throw new IllegalArgumentException("Money cannot be negative");
        return money <= getMoney(userId);
    }

    public boolean hasEnoughBankMoney(int userId, double money) {
        if (money < 0) throw new IllegalArgumentException("Money cannot be negative");
        return money <= getBankMoney(userId);
    }

    public void payMoneyToPlayer(int userId, int targetId, double money) {
        if (hasEnoughMoney(userId, money)) {
            removeMoney(userId, money);
            addMoney(targetId, money);
        }
    }

    public void payMoneyToBank(int userId, double money) {
        if (hasEnoughMoney(userId, money)) {
            removeMoney(userId, money);
            addBankMoney(userId, money);
        }
    }

    private enum Procedure {
        ECONOMY_CREATE_USER("CB_Economy_CreateUser", "uid INT, current DOUBLE, bank DOUBLE", "INSERT INTO [TABLE] VALUES (uid, current, bank);"),
        ECONOMY_DELETE_USER("CB_Economy_DeleteUser", "uid INT", "DELETE FROM [TABLE] WHERE UserID=uid;"),
        ECONOMY_UPDATE_MONEY("CB_Economy_Update_Money", "uid INT, current DOUBLE", "UPDATE [TABLE] SET Money=current WHERE UserID=uid;"),
        ECONOMY_UPDATE_BANK("CB_Economy_Update_BankMoney", "uid INT, bank DOUBLE", "UPDATE [TABLE] SET BankMoney=bank WHERE UserID=uid;"),
        ECONOMY_GET_USER("CB_Economy_GetUser", "uid INT", "SELECT * FROM [TABLE] WHERE UserID=uid;");
        private static final Procedure[] VALUES = values();

        private final String name;
        private final String query;

        Procedure(final String name, final String input, final String query) {
            this.name = name;
            this.query = Database.getProcedureQueryWithoutObjects(name, input, query);
        }

        public String getName() {
            return name;
        }

        public String getQuery(String tableName) {
            return query.replace("[TABLE]", tableName);
        }

        public static void loadAll(String tableName) {
            for (Procedure procedure : VALUES) Database.update(procedure.getQuery(tableName));
        }
    }
}
