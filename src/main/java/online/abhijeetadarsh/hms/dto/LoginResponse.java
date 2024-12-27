package online.abhijeetadarsh.hms.dto;

public class LoginResponse {
    private String accessToken;
    private User user;

    private static class User {
        private int id;
        private String username;
        private String role;

        public User(int id, String username, String role) {
            this.id = id;
            this.username = username;
            this.role = role;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }

    public LoginResponse(String accessToken, int id, String username, String role) {
        this.accessToken = accessToken;
        this.user = new User(id, username, role);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return user;
    }
}

