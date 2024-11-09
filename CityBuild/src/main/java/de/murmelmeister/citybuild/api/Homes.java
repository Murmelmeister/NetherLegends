package de.murmelmeister.citybuild.api;

import de.murmelmeister.murmelapi.utils.Database;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.List;

public class Homes {

    public Homes() {
        String tableName = "CB_Home";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "UserID INT, HomeName VARCHAR(100), PRIMARY KEY (UserID, HomeName), WorldName VARCHAR(100), WorldType VARCHAR(100), " +
                                        "X DOUBLE, Y DOUBLE, Z DOUBLE, Yaw DOUBLE, Pitch DOUBLE");
    }

    public boolean hasHome(int userId, String homeName) {
        return Database.callExists(Procedure.HOME_GET.getName(), userId, homeName);
    }

    public void addHome(int userId, String homeName, Location location) {
        if (homeName.length() > 100) throw new IllegalArgumentException("Home name cannot be longer than 100 characters");
        if (hasHome(userId, homeName)) return;
        String worldName = location.getWorld().getName();
        String environment = location.getWorld().getEnvironment().name();
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY() + 0.25D;
        double z = location.getBlockZ() + 0.5D;
        double yaw = Math.round(location.getYaw() / 45.0F) * 45;
        double pitch = Math.round(location.getPitch() / 45.0F) * 45;
        Database.callUpdate(Procedure.HOME_ADD.getName(), userId, homeName, worldName, environment, x, y, z, yaw, pitch);
    }

    public void removeHome(int userId, String homeName) {
        Database.callUpdate(Procedure.HOME_REMOVE.getName(), userId, homeName);
    }

    public Location getHome(Server server, int userId, String homeName) {
        String worldName = Database.callQuery(null, "WorldName", String.class, Procedure.HOME_GET.getName(), userId, homeName);
        double x = Database.callQuery(0.0D, "X", double.class, Procedure.HOME_GET.getName(), userId, homeName);
        double y = Database.callQuery(0.0D, "Y", double.class, Procedure.HOME_GET.getName(), userId, homeName);
        double z = Database.callQuery(0.0D, "Z", double.class, Procedure.HOME_GET.getName(), userId, homeName);
        double yaw = Database.callQuery(0.0D, "Yaw", double.class, Procedure.HOME_GET.getName(), userId, homeName);
        double pitch = Database.callQuery(0.0D, "Pitch", double.class, Procedure.HOME_GET.getName(), userId, homeName);

        World world = server.getWorld(worldName);
        return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }

    public List<String> getHomes(int userId) {
        return Database.callQueryList("HomeName", String.class, Procedure.HOME_GET_ALL.getName(), userId);
    }

    private enum Procedure {
        HOME_ADD("Home_Add", "uid INT, hname VARCHAR(100), wname VARCHAR(100), wtype VARCHAR(100), wx DOUBLE, wy DOUBLE, wz DOUBLE, wyaw DOUBLE, wp DOUBLE",
                "INSERT INTO [TABLE] VALUES (uid, hname, wname, wtype, wx, wy, wz, wyaw, wp);"),
        HOME_REMOVE("Home_Remove", "uid INT, hname VARCHAR(100)", "DELETE FROM [TABLE] WHERE UserID=uid AND HomeName=hname;"),
        HOME_GET("Home_Get", "uid INT, hname VARCHAR(100)", "SELECT * FROM [TABLE] WHERE UserID=uid AND HomeName=hname;"),
        HOME_GET_ALL("Home_GetAll", "uid INT", "SELECT * FROM [TABLE] WHERE UserID=uid;");
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
