package pl.coderslab.workshop2.codingschool.model;

import java.sql.*;
import java.util.ArrayList;

public class Solution {
    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int exercise_id;
    private long users_id;

    public int getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(long users_id) {
        this.users_id = users_id;
    }

public Solution (){}

public Solution (String description, int exercise_id, long users_id){
    this.description = description;
    this.exercise_id = exercise_id;
    this.users_id = users_id;
    this.created = new Timestamp(System.currentTimeMillis());
    this.updated = new Timestamp(System.currentTimeMillis());
}

public String toString (){
    StringBuilder sb = new StringBuilder("Solution:\n");
    sb.append(String.format("* Id: %d\n", this.id));
    sb.append(String.format("* Created: %s\n", this.created));
    sb.append(String.format("* Updated: %s\n", this.updated));
    sb.append(String.format("* Description: %s\n", this.description));
    sb.append(String.format("* Exercise Id: %d\n", this.exercise_id));
    sb.append(String.format("* User Id: %d\n", this.users_id));
    return sb.toString();
}

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(description, exercise_id, users_id, created) VALUES (?, ?, ?, ?)";
            String[] generatedColumns = {"id"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.exercise_id);
            preparedStatement.setLong(3, this.users_id);
            preparedStatement.setTimestamp(4, this.created);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET description = ?, exercise_id = ?, users_id = ?, updated = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.exercise_id);
            preparedStatement.setLong(3, this.users_id);
            preparedStatement.setTimestamp(4, this.updated);
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id= ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getLong("users_id");
            return loadedSolution;
        }
        return null;
    }

    static public ArrayList<Solution> loadAllSolutions (Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getTimestamp("created");
            loadedSolution.updated = resultSet.getTimestamp("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getLong("users_id");
            solutions.add(loadedSolution);
        }
        return solutions;
    }

    public void delete (Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    //DRUGI SPOSOB NA USUNIECIE SOLUTION
    static public void deleteSolution(Connection conn, Solution solution) throws  SQLException {
        if (solution.id != 0) {
            String sql = "DELETE FROM solution WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, solution.id);
            preparedStatement.executeUpdate();
            solution.id = 0;
        }
    }

}
